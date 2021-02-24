package com.example.java.atomix;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.protocols.backup.partition.PrimaryBackupPartitionGroup;
import org.springframework.stereotype.Service;

@Service
public class AtomixManager {

    private Atomix atomix;

    public AtomixManager(){
         atomix = Atomix.builder()
                .withMemberId("member-1")
                .withAddress("10.5.51.42:5682")
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes(Node.builder()
                                .withId("member1")
                                .withAddress("10.5.51.42:5683")
                                .build())
                        .build())
                .withManagementGroup(PrimaryBackupPartitionGroup.builder("system")
                        .withNumPartitions(1)
                        .build())
                .withPartitionGroups(PrimaryBackupPartitionGroup.builder("data")
                        .withNumPartitions(32)
                        .build())
                .build();
        atomix.start().join();
    }
    public Atomix getAtomix(){
        return atomix;
    }

}
