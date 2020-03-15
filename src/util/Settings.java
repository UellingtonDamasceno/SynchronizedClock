package util;

/**
 *
 * @author Uellington Conceição
 */
public class Settings {

     public enum Connection{
        DEFAULT_PORT(9999),
        DEFAULT_IP(127001);
    
        private final int value;
     
        private Connection(int value){
            this.value = value;
        }
        
        @Override
        public String toString(){
            return (this.value == 9999) ? String.valueOf(value) : "127.0.0.1"; 
        }
    }
    
    public enum Icons {
        MAIN_ICON("main");

        private final String name;
        private final String ORIGIN = "/resources/icons/";
        private final String EXTENSION = ".png";

        private Icons(String name) {
            this.name = name;
        }

        public String getIconPath() {
            return this.ORIGIN + this.name + this.EXTENSION;
        }
    }

    public enum Scenes {
        MAIN("Main.fxml", "Menu", false);

        private final String name;
        private final String title;
        private final boolean cache;

        private Scenes(String value, String title, boolean cache) {
            this.name = "/view/" + value;
            this.title = title;
            this.cache = cache;
        }

        public String getTitle() {
            return this.title;
        }

        public boolean isCache() {
            return this.cache;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
