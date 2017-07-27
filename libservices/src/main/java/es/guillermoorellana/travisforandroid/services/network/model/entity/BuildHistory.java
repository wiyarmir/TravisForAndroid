
package es.guillermoorellana.travisforandroid.services.network.model.entity;

import java.util.List;

import static java.util.Collections.emptyList;

public class BuildHistory {
    @SuppressWarnings("PMD.ImmutableField")
    private List<Build> builds = emptyList();
    @SuppressWarnings("PMD.ImmutableField")
    private List<Commit> commits = emptyList();

    public List<Build> getBuilds() {
        return builds;
    }

    public List<Commit> getCommits() {
        return commits;
    }
}
