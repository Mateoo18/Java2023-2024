package org.smartcity;

public class Shop extends Building{
        private String type;

        public Shop(String address, int floors, String type) {
            super(address, floors);
            this.type = type;
        }

        @Override
        public void operate() {
            System.out.println("Shop  at: "+getAddress()+" is a :  "+type);

        }
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
}
