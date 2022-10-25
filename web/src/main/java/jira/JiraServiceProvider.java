package jira;

import com.sun.jersey.core.util.Base64;
import net.rcarz.jiraclient.*;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class JiraServiceProvider {
    private JiraClient Jira;
    private String project;
    private String JiraUrl;
    public JiraServiceProvider(String JiraUrl, String username, String password, String project) {

        this.JiraUrl = JiraUrl;

        // create basic authentication object
        BasicCredentials creds = new BasicCredentials(username, password);
        // initialize the Jira client with the url and the credentials
        Jira = new JiraClient(JiraUrl, creds);
        this.project = project;
    }
    public void createJiraIssue(String issueType, String summary, String description, String reporterName) {

        try {
            //Avoid Creating Duplicate Issue
//            Issue.SearchResult sr = Jira.searchIssues("Description ~ \"" + description + "\"");
//            if (sr.total != 0) {
//                System.out.println("Same Issue Already Exists on Jira");
//                return;
//            }
            //Create issue if not exists
            Issue.FluentCreate fleuntCreate = Jira.createIssue(project, issueType);
            fleuntCreate.field(Field.SUMMARY, summary);
            fleuntCreate.field(Field.DESCRIPTION, description);
            fleuntCreate.field(Field.LABELS, "Bugs");
            Issue newIssue = fleuntCreate.execute();
            System.out.println("********************************************");
            System.out.println("New issue created in Jira with ID: " + newIssue);
            System.out.println("New issue URL is :" + JiraUrl + "/browse/" + newIssue);
            System.out.println("*******************************************");

        } catch (JiraException e) {
            e.printStackTrace();
        }
    }

    public void createJiraIssueForFailure(String issueType, String summary, String description) throws IOException {
        String key = "DH";
//        String issueType = "Bug";
//        String summary = "First bug Record";
//        String description = "{code}DB.EXECUTION.ERROR{code} Got 500 as Htp Status Code";

        String auth = new String(Base64.encode("62a07983e318a50069383cd5" + ":" + "3eTYaEDjL6Y3DYzdziHU2942"));

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://digital-hub-delivery.atlassian.net/rest/api/2/issue");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic "+auth);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");

        String body = "{\"fields\":{\"project\":{\"key\":\""+key+"\"},\"summary\":\""+summary+"\",\"description\":\""+description+"\",\"issuetype\":{\"name\":\""+issueType+"\"}}}";
        StringEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);

        HttpResponse response = client.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Body: " +responseBody);
        System.out.println("Status Code: " +statusCode);
    }
}
