package es.guillermoorellana.travisforandroid.data;

import com.raizlabs.android.dbflow.structure.provider.ContentUtils;

import java.util.List;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.model.Build;
import es.guillermoorellana.travisforandroid.model.GHCommit;
import es.guillermoorellana.travisforandroid.services.network.TravisApi;
import es.guillermoorellana.travisforandroid.services.network.model.entity.BuildHistory;
import es.guillermoorellana.travisforandroid.services.network.model.entity.Repo;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

public class Repository {

    private final TravisApi api;

    @Inject
    public Repository(TravisApi travisApi) {
        api = travisApi;
    }

    public Single<Integer> getRepos() {
        return getReposCommon(api.repos());
    }

    private Single<Integer> getReposCommon(Single<List<Repo>> apiCall) {
        return apiCall.toObservable()
                .flatMapIterable(repos -> repos)
                .map(ApiAdapter::fromApi)
                .toList()
                .flatMap((Func1<List<es.guillermoorellana.travisforandroid.model.Repo>, Observable<Integer>>) repos -> Observable.create(
                                subscriber -> {
                                    ContentUtils.bulkInsert(TravisDatabase.REPO_MODEL.CONTENT_REPO_URI, es.guillermoorellana.travisforandroid.model.Repo.class, repos);
                                    subscriber.onNext(repos.size());
                                    subscriber.onCompleted();
                                })
                )
                .toSingle();
    }

    public Single<Integer> getRepos(String search) {
        return getReposCommon(api.repos(search));
    }

    public Observable<Integer> getBuildHistory(long repoId) {
        return api.buildHistory(repoId)
                .toObservable()
                .flatMap(apiBuildHistory -> getBuilds(apiBuildHistory).mergeWith(getCommits(apiBuildHistory)));
    }

    public Observable<Integer> getPRHistory(long repoId) {
        return api.buildHistoryByEventType(repoId, "pull_request")
                .toObservable()
                .flatMap(apiBuildHistory -> getBuilds(apiBuildHistory).mergeWith(getCommits(apiBuildHistory)));
    }

    private Single<Integer> getBuilds(BuildHistory buildHistory) {
        return Observable.just(buildHistory.getBuilds())
                .flatMapIterable(apiBuilds -> apiBuilds)
                .map(ApiAdapter::fromApi)
                .toList()
                .flatMap((Func1<List<Build>, Observable<Integer>>)
                                builds -> Observable.create(
                                        subscriber -> {
                                            ContentUtils.bulkInsert(TravisDatabase.BUILD_MODEL.URI, Build.class, builds);
                                            subscriber.onNext(builds.size());
                                            subscriber.onCompleted();
                                        }
                                )
                )
                .toSingle();
    }

    private Single<Integer> getCommits(BuildHistory buildHistory) {
        return Observable.just(buildHistory.getCommits())
                .flatMapIterable(apiCommits -> apiCommits)
                .map(ApiAdapter::fromApi)
                .toList()
                .flatMap((Func1<List<GHCommit>, Observable<Integer>>) commits -> Observable.create(
                        subscriber -> {
                            ContentUtils.bulkInsert(TravisDatabase.COMMIT_MODEL.CONTENT_COMMIT_URI, GHCommit.class, commits);
                            subscriber.onNext(commits.size());
                            subscriber.onCompleted();
                        }
                ))
                .toSingle();
    }
}
