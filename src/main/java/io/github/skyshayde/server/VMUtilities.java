package io.github.skyshayde.server;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VMUtilities {
    private static final String APPLICATION_NAME = "Skyshayde-AlfredBot";
    private static final String PROJECT_ID = "alfredbot-199021";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;
    private static final String ZONE_NAME = "us-central1-f";
    private static final List<String> SCOPES = Arrays.asList(ComputeScopes.COMPUTE);
    private static final String NETWORK_INTERFACE_CONFIG = "ONE_TO_ONE_NAT";
    private static final String NETWORK_ACCESS_CONFIG = "External NAT";
    private static final String SOURCE_IMAGE_PREFIX = "https://www.googleapis.com/compute/v1/projects/";
    private static final String SOURCE_IMAGE_PATH = "debian-cloud/global/images/debian-7-wheezy-v20150710";


    public static boolean printInstances(Compute compute) throws IOException {
        System.out.println("================== Listing Compute Engine Instances ==================");
        Compute.Instances.List instances = compute.instances().list(PROJECT_ID, ZONE_NAME);
        InstanceList list = instances.execute();
        boolean found = false;
        if (list.getItems() == null) {
            System.out.println("No instances found. Sign in to the Google Developers Console and create "
                    + "an instance at: https://console.developers.google.com/");
        } else {
            for (Instance instance : list.getItems()) {
                System.out.println(instance.toPrettyString());
            }
        }
        return found;
    }

    public static Disk getDisk(Compute compute, String name) throws IOException {
        return compute.disks().get(PROJECT_ID, ZONE_NAME, name).execute();
    }

    public static void createDisk(Compute compute, String name) throws IOException {
        Disk disk = new Disk();
        disk.setName(name);
        disk.setSizeGb(10L);
        disk.setType("/zones/" + ZONE_NAME + "/diskTypes/pd-standard");
        compute.disks().insert(PROJECT_ID, ZONE_NAME, disk).execute();
    }

    public static void createInstance(Compute compute, String name) {
        Instance instance = new Instance();
        instance.setName(name);
        instance.setMachineType("https://www.googleapis.com/compute/v1/projects/" + PROJECT_ID +
                "/zones/" + ZONE_NAME + "/machineTypes/f1-micro");

        NetworkInterface ifc = new NetworkInterface();
        ifc.setNetwork("https://www.googleapis.com/compute/v1/projects/" + PROJECT_ID + "/global/networks/default");
        List<AccessConfig> configs = new ArrayList<>();
        AccessConfig config = new AccessConfig();
        config.setType(NETWORK_INTERFACE_CONFIG);
        config.setName(NETWORK_ACCESS_CONFIG);
        configs.add(config);
        ifc.setAccessConfigs(configs);
        instance.setNetworkInterfaces(Collections.singletonList(ifc));

        AttachedDisk disk = new AttachedDisk();
        disk.setBoot(true);
        disk.setAutoDelete(true);
        disk.setType("PERSISTENT");
        AttachedDiskInitializeParams params = new AttachedDiskInitializeParams();
        // Assign the Persistent Disk the same name as the VM Instance.
        params.setDiskName(name);
        // Specify the source operating system machine image to be used by the VM Instance.
        params.setSourceImage(SOURCE_IMAGE_PREFIX + SOURCE_IMAGE_PATH);
        // Specify the disk type as Standard Persistent Disk
        params.setDiskType("https://www.googleapis.com/compute/v1/projects/" + PROJECT_ID + "/zones/"
                + ZONE_NAME + "/diskTypes/pd-standard");
        disk.setInitializeParams(params);
        instance.setDisks(Collections.singletonList(disk));

        try {
            compute.instances().insert(PROJECT_ID, ZONE_NAME, instance).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Operation deleteInstance(Compute compute, String instanceName) throws Exception {
        System.out.println("================== Deleting Instance " + instanceName + " ==================");
        Compute.Instances.Delete delete = compute.instances().delete(PROJECT_ID, ZONE_NAME, instanceName);
        return delete.execute();
    }
}
