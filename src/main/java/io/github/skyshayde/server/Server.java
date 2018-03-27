package io.github.skyshayde.server;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.*;
import io.github.skyshayde.Token;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Server {

    private static final String APPLICATION_NAME = "Skyshayde-AlfredBot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;
    private static final List<String> SCOPES = Arrays.asList(ComputeScopes.COMPUTE);


    private final String instanceName;
    private final String diskName;

    public Server(String instanceName, String diskName) {
        this.instanceName = instanceName;
        this.diskName = diskName;
        Compute compute = null;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            InputStream stream = new Token().getClass().getClassLoader().getResourceAsStream("client_secret.json");
            GoogleCredential credential = GoogleCredential.fromStream(stream).createScoped(SCOPES);
            compute = new Compute.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            VMUtilities.createDisk(compute, diskName);
//            createInstance(compute, instanceName);
//            printInstances(compute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
