#!groovy
import java.util.regex.*

void getRepos(String repoUrl) {

  def userApi = new URL(repoUrl)
  def repos = new groovy.json.JsonSlurper().parse(userApi.newReader())
  repos.each {
    def repoName = it.name
    //def jobName = "${project}-${branchName}".replaceAll('/','-')
    /*job(jobName) {
        scm {
            git("git://github.com/${project}.git", branchName)
        }
        steps {
            maven("test -Dproject.name=${project}/${branchName}")
        }
    }
*/
    out.println(repoName)
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
