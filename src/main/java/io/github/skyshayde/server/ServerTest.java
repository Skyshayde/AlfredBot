package io.github.skyshayde.server;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.*;
import io.github.skyshayde.Token;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerTest {

    private static final String APPLICATION_NAME = "Skyshayde-AlfredBot";
    private static final String projectId = "alfredbot-199021";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;
    private static final String zone = "us-central1-f";
    private static final List<String> SCOPES = Arrays.asList(ComputeScopes.COMPUTE);
    private static final String NETWORK_INTERFACE_CONFIG = "ONE_TO_ONE_NAT";
    private static final String NETWORK_ACCESS_CONFIG = "External NAT";
    private static final String SOURCE_IMAGE_PREFIX = "https://www.googleapis.com/compute/v1/projects/";
    private static final String SOURCE_IMAGE_PATH = "debian-cloud/global/images/debian-7-wheezy-v20150710";

    public static void main(String[] args) {
        new Server("test", "test");
    }
}
