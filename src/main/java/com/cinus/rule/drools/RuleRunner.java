package com.cinus.rule.drools;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.File;
import java.util.Collection;

public class RuleRunner {

    public RuleRunner() {
    }

    public void runRules(String[] rules, Object[] facts) {

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        for (int i = 0; i < rules.length; i++) {
            String ruleFile = rules[i];
            System.out.println("Loading file: " + ruleFile);
//            kbuilder.add( ResourceFactory.newClassPathResource( ruleFile,
//                    RuleRunner.class ),
//                    ResourceType.DRL );
            kbuilder.add(ResourceFactory.newFileResource(new File(ruleFile)), ResourceType.DRL);
        }

        Collection<KiePackage> pkgs = kbuilder.getKnowledgePackages();
        kbase.addPackages(pkgs);
        KieSession ksession = kbase.newKieSession();

        for (int i = 0; i < facts.length; i++) {
            Object fact = facts[i];
            System.out.println("Inserting fact: " + fact);
            ksession.insert(fact);
        }

        ksession.fireAllRules();
    }
}
