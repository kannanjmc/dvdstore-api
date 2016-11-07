package com.scottwseo.dvdstore;

/**
 * Created by seos on 11/6/16.
 */
public class DVDStoreApplicationLauncher {

    public static void main(String[] args) throws Exception {
        new DVDStoreApplication().run("server", "dvdstore-api/conf/dvdstore-api.yml");
    }

}
