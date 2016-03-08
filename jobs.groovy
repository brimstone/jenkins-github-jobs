#!groovy
import java.util.regex.*

void getRepos(String repoUrl) {

  def userApi = new URL(repoUrl)
  def repos = new groovy.json.JsonSlurper().parse(userApi.newReader())
  repos.each {
    def repoName = it.name
	out.println(repoName)
    clone_url = it.clone_url
    //def jobName = "${project}-${branchName}".replaceAll('/','-')
    workflowJob(repoName) {
        scm {
            git(clone_url)
        }
	    triggers {
		    scm('H/15 * * * *')
		}
    }
  }
  URLConnection conn = userApi.openConnection();
  Pattern r = Pattern.compile("(http[^>]*)>; rel=\"next\"")
  Matcher m = r.matcher(conn.getHeaderField("Link"))

  if (m.find()) {
    out.println("Following next url, " + m.group(1))
    getRepos(m.group(1))
  }
}
getRepos("https://api.github.com/users/brimstone/repos")
