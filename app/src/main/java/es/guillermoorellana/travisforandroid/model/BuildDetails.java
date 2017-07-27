package es.guillermoorellana.travisforandroid.model;

public class BuildDetails {
    private Build build;
    private GHCommit commit;
    private Job job;

    public Build getBuild() {
        return build;
    }

    public GHCommit getCommit() {
        return commit;
    }

    public Job getJob() {
        return job;
    }
}
