package org.revo.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExtractText {

    public static ParallelFlux<AzlyricdbSong> extract(String artist) {
        String site = "http://www.azlyricdb.com";
        return
                Flux.just(t2(site, "/artist/"+artist))
                        .parallel(4).runOn(Schedulers.parallel())
                        .flatMap(it -> Flux.fromIterable(it.getUrl()))
                        .map(ExtractText::t1)
                        .filter(Objects::nonNull);
    }

    static private AzlyricdbSong t1(String url) {
        try {
            Source source = new Source(new URL(url));
            source.fullSequentialParse();
            List<Element> linkElements = source.getAllElements(HTMLElementName.DIV);
            for (Element linkElement : linkElements) {
                if (Objects.equals("lrc", linkElement.getAttributeValue("id"))) {
                    String words = linkElement.getContent().getTextExtractor().toString();
                    if (words != null && !words.trim().isEmpty())
                        return new AzlyricdbSong(getTitle(source), words);
                }
            }
        } catch (IOException e) {
        }
        return null;
    }

    static private AzlyricdbArtist t2(String site, String artist) {
        AzlyricdbArtist azlyricdbArtist = new AzlyricdbArtist();
        try {
            Source source = new Source(new URL(site + artist));
            source.fullSequentialParse();
            List<Element> E1 = source.getAllElements(HTMLElementName.TR);
            for (Element E11 : E1) {
                List<Element> E2 = E11.getContent().getAllElements(HTMLElementName.TD);
                for (Element E21 : E2) {
                    for (Element a : E21.getAllElements(HTMLElementName.A)) {
                        String href = a.getAttributeValue("href");
                        if (!href.contains("login") && !href.contains("register"))
                            azlyricdbArtist.getUrl().add(site + href);
                    }
                }

            }
        } catch (IOException e) {

        }
        return azlyricdbArtist;
    }

    static public AzlyricdbLetter t3(String site, String letter) {
        AzlyricdbLetter azlyricdbLetter = new AzlyricdbLetter();
        try {
            Source source = new Source(new URL(site + letter));
            source.fullSequentialParse();
            List<Element> E1 = source.getAllElements(HTMLElementName.TR);
            for (Element E11 : E1) {
                List<Element> E2 = E11.getContent().getAllElements(HTMLElementName.TD);
                for (Element E21 : E2) {
                    for (Element a : E21.getAllElements(HTMLElementName.A)) {
                        String href = a.getAttributeValue("href");
                        if (href.contains("/artist/"))
                            azlyricdbLetter.getUrl().add(href);
                    }
                }
            }
        } catch (IOException e) {

        }
        return azlyricdbLetter;
    }

    private static String getTitle(Source source) {
        Element titleElement = source.getFirstElement(HTMLElementName.TITLE);
        if (titleElement == null) return null;
        return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
    }

    /**
     * Created by ashraf on 25/02/17.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AzlyricdbLetter {
        private List<String> url = new ArrayList<>();
    }

    /**
     * Created by ashraf on 25/02/17.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AzlyricdbSong {
        private String title;
        private String words;
    }

    /**
     * Created by ashraf on 25/02/17.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AzlyricdbArtist {
        private List<String> url = new ArrayList<>();
    }
}