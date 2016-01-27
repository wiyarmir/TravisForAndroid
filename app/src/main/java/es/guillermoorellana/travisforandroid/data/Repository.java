/*
 *   Copyright 2016 Guillermo Orellana Ruiz
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package es.guillermoorellana.travisforandroid.data;

import com.raizlabs.android.dbflow.structure.provider.ContentUtils;

import java.util.List;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entity.ApiBuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;
import es.guillermoorellana.travisforandroid.model.Build;
import es.guillermoorellana.travisforandroid.model.GHCommit;
import es.guillermoorellana.travisforandroid.model.Repo;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

public class Repository {

    private final TravisRestApi api;

    @Inject
    public Repository(TravisRestApi travisRestApi) {
        api = travisRestApi;
    }

    public Single<Integer> getRepos() {
        return getReposCommon(api.repos());
    }

    private Single<Integer> getReposCommon(Single<List<ApiRepo>> apiCall) {
        return apiCall.toObservable()
                .flatMapIterable(repos -> repos)
                .map(ApiAdapter::fromApi)
                .toList()
                .flatMap((Func1<List<Repo>, Observable<Integer>>) repos -> Observable.create(
                                subscriber -> {
                                    ContentUtils.bulkInsert(TravisDatabase.REPO_MODEL.CONTENT_REPO_URI, Repo.class, repos);
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

    private Single<Integer> getBuilds(ApiBuildHistory apiBuildHistory) {
        return Observable.just(apiBuildHistory.getBuilds())
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

    private Single<Integer> getCommits(ApiBuildHistory apiBuildHistory) {
        return Observable.just(apiBuildHistory.getCommits())
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
