package jira;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public@interface JiraCreateIssue{

        boolean isCreateIssue();

        }