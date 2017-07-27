
package es.guillermoorellana.travisforandroid.services.network.model.entity;

public class BuildDetails {
    private Build build;
    private Commit commit;
    private Job job;

    public Build getBuild() {
        return build;
    }

    public Commit getCommit() {
        return commit;
    }

    public Job getJob() {
        return job;
    }
}
