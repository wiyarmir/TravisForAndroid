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

package es.guillermoorellana.travisforandroid.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Period;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.model.Build;
import es.guillermoorellana.travisforandroid.model.GHCommit;

public class BuildView extends LinearLayout {

    @BindView(R.id.build_number)
    TextView buildNumber;

    @BindView(R.id.build_state)
    TextView buildState;

    @BindView(R.id.build_pull_request_title)
    TextView pullRequestTitle;

    @BindView(R.id.build_branch)
    TextView buildBranch;

    @BindView(R.id.build_commit_message)
    TextView buildCommitMessage;

    @BindView(R.id.build_commit_person)
    TextView buildCommitPerson;

    @BindView(R.id.build_duration)
    TextView buildDuration;

    @BindView(R.id.build_finished)
    TextView buildFinished;

    public BuildView(Context context) {
        super(context);
        initializeViews(context);
    }

    public BuildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public BuildView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_build, this);
        ButterKnife.bind(this, view);
    }

    private void showPullRequestDetails(boolean isPullRequest) {
        if (isPullRequest) {
            pullRequestTitle.setVisibility(VISIBLE);
            buildBranch.setVisibility(GONE);
            buildCommitMessage.setVisibility(GONE);
        } else {
            pullRequestTitle.setVisibility(GONE);
            buildBranch.setVisibility(VISIBLE);
            buildCommitMessage.setVisibility(VISIBLE);
        }
    }

    private void setBuildState(String state) {
        if (!TextUtils.isEmpty(state)) {
            int buildColor = getBuildColor(state);
            buildState.setText(state);
            buildState.setTextColor(buildColor);
            buildNumber.setTextColor(buildColor);

            buildNumber.setCompoundDrawablesWithIntrinsicBounds(getBuildImage(state), 0, 0, 0);
        }
    }

    private int getBuildImage(String state) {
        switch (state) {
            case "created":
            case "started":
                return R.drawable.commit;
            case "passed":
                return R.drawable.check;
            case "canceled":
            case "errored":
                return R.drawable.x;
            case "failed":
                return R.drawable.x;
            default:
                return R.drawable.github;
        }
    }

    private int getBuildColor(String state) {
        switch (state) {
            case "created":
            case "started":
                return Color.YELLOW;
            case "passed":
                return Color.GREEN;
            case "canceled":
            case "failed":
            case "errored":
                return Color.RED;
            default:
                return Color.DKGRAY;
        }
    }

    private void setBuildDuration(long durationInMillis) {
        if (durationInMillis == 0) {
            String stateInProgress = getContext().getString(R.string.build_build_in_progress);
            String duration = getContext().getString(R.string.build_duration, stateInProgress);
            buildDuration.setText(duration);
        } else {
            String duration = new Period(durationInMillis).toString();
            duration = getContext().getString(R.string.build_duration, duration);
            buildDuration.setText(duration);
        }
    }

    private void setBuildFinishedAt(long finishedAt) {
        if (finishedAt == 0) {
            String stateInProgress = getContext().getString(R.string.build_build_in_progress);
            String finished = getContext().getString(R.string.build_finished_at, stateInProgress);
            buildFinished.setText(finished);
        } else {
            String formattedDate = new DateTime(finishedAt).toString();
            formattedDate = getContext().getString(R.string.build_finished_at, formattedDate);
            buildFinished.setText(formattedDate);
        }
    }

    public void setBuildData(Build build, GHCommit commit) {
        showPullRequestDetails(false);

        buildNumber.setText(getContext().getString(R.string.build_build_number, build.getNumber()));
        setBuildState(build.getState());

        if (commit != null) {
            buildBranch.setText(commit.getBranch());
            buildCommitMessage.setText(commit.getMessage());
            buildCommitPerson.setText(commit.getCommitterName());
        }

        setBuildFinishedAt(build.getFinishedAt());

        setBuildDuration(build.getDuration());
    }
}
