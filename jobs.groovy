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
  Pattern r = Pattern.compile("(http[^ >;]*); rel=\"next\"")
  Matcher m = r.matcher(conn.getHeaderField("Link"))

  out.Println(m.length)
/*  String next = m[0]

  if (next != ) {
    out.println("Following next url, " + next)
		getRepos(next)
  }
*/
}
getRepos("https://api.github.com/users/brimstone/repos")
