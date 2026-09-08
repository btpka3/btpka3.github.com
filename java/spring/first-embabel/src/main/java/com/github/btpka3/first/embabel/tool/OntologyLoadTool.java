package com.github.btpka3.first.embabel.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OntologyLoadTool implements Tool {

    private final CommandExecutor commandExecutor;
    private final ObjectMapper objectMapper;

    @Override
    public String name() {
        return "ontology_load";
    }

    @Override
    public String description() {
        return "Load UModel ontology schema for a domain. Args: domain, addr (optional, default daily).";
    }

    @Override
    @SneakyThrows
    public String execute(Map<String, String> args) {
        String domain = args.getOrDefault("domain", "riskctl_base");
        String addr = args.getOrDefault("addr", "https://daily-uf-mtee-ontology.alibaba.net");
        String spl = ".umodel with(kind='entity_set', domain='" + domain + "') | project name,display_name | limit 200";
        String output = commandExecutor.execute(
                "umctl --addr " + addr + " query run riskctl_base \"" + spl + "\"");
        return output;
    }
}
