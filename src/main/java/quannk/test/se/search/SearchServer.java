package quannk.test.se.search;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quannk.test.se.index.Document;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;
import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import java.io.File;
import java.util.List;

/**
 * simple web-server to serve search without restart the whole stuffs
 */
public class SearchServer {
	private static final Logger LOG = LoggerFactory.getLogger(SearchServer.class);

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("Usage: <data_file> [optional_port]");
			System.exit(1);
		}
		Runner searchRunner = new Runner(new File(args[0]));
		int port = args.length > 1 ? Integer.parseInt(args[1]) : 5050;

		RatpackServer.start(s -> s
				.serverConfig(c -> c
						.baseDir(BaseDir.find())
						.port(port)
						.env())

				.registry(Guice.registry(b -> {
					b.module(TextTemplateModule.class, conf -> conf.setStaticallyCompile(true));
				}))

				.handlers(chain -> chain
						.get("help", ctx -> ctx.render("1 2 3 Sound check!\nCommand: reload, info, next, submit"))

						.get("search", ctx -> {
							String q = ctx.getRequest().getQueryParams().get("q");
							String algorithm = ctx.getRequest().getQueryParams().get("algorithm");
							boolean useBM25 = "bm25".equalsIgnoreCase(algorithm);
							LOG.info("Received request searching for (bm25={}) q=[{}]", useBM25, q);
							final List<Pair<Score, Document>> searchResult = searchRunner.search(q, 10, useBM25);
							class SR {
								public final double score;
								public final int id;
								public final String product;

								private SR(double score, int id, String product) {
									this.score = score;
									this.id = id;
									this.product = product;
								}
							}
							class SearchResult {
								public final String query;
								public final SR[] result;

								private SearchResult(String query, SR[] serp) {
									this.query = query;
									this.result = serp;
								}
							}
							final SR[] srs = searchResult.stream().map(p -> new SR(((DoubleScore) p.getValue0()).getScore(), p.getValue1().getId().getId(), p.getValue1().getRawContent())).toArray(SR[]::new);
							ctx.render(Jackson.json(new SearchResult(q, srs)));
						})

						.files(f -> f.dir("public"))
				)
		);
	}
}
