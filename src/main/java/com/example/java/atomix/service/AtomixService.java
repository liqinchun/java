package com.example.java.atomix.service;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.tree.AsyncAtomicDocumentTree;
import io.atomix.protocols.backup.MultiPrimaryProtocol;
import io.atomix.protocols.backup.partition.PrimaryBackupPartitionGroup;
import io.atomix.utils.net.Address;
import org.springframework.stereotype.Service;

@Service
public class AtomixService<V> {

    private final AsyncAtomicDocumentTree<String> atomixTree;

    Atomix atomix = Atomix.builder()
            .withMemberId("member-1")
            .withAddress("10.5.51.42:5679")
            .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                    .withNodes(Node.builder()
                            .withId("member1")
                            .withAddress(Address.from("10.5.51.42:5679"))
                            .build())
                    .build())
            .withManagementGroup(PrimaryBackupPartitionGroup.builder("system")
                    .withNumPartitions(1)
                    .build())
            .withPartitionGroups(PrimaryBackupPartitionGroup.builder("data")
                    .withNumPartitions(32)
                    .build())
            .build();

    public AtomixService(AsyncAtomicDocumentTree<String> atomixTree) {
        this.atomixTree = atomixTree;
    }

    public AtomixService() {
        atomix.start().join();
        this.atomixTree = atomix.<String>atomicDocumentTreeBuilder("my-tree")
                .withProtocol(MultiPrimaryProtocol.builder()
                        .build())
                .build().async();
    }

    public String name() {
        return atomixTree.name();
    }



}
