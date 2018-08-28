package xyz.bnayagrawal.android.newsreader.data.model;

import java.util.List;

public final class Sources {

    private String status;
    private List<SourceDetails> sources;

    public String getStatus() {
        return status;
    }

    public List<SourceDetails> getSources() {
        return sources;
    }

    public class SourceDetails {

        private String id;
        private String name;
        private String description;
        private String url;
        private String category;
        private String language;
        private String country;

        public SourceDetails(String id, String name, String description, String url, String category, String language, String country) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.url = url;
            this.category = category;
            this.language = language;
            this.country = country;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getCategory() {
            return category;
        }

        public String getLanguage() {
            return language;
        }

        public String getCountry() {
            return country;
        }
    }
}
