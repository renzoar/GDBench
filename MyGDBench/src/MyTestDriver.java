
/**
 *
 * @author renzo
 */
public class MyTestDriver extends TestDriver {

    public MyTestDriver() {
    }

    public void printDBinfo() {
    }

    @Override
    public boolean createDB(String dbname) {
        return true;
    }

    @Override
    public boolean openDB(String dbname) {
        return true;
    }

    @Override
    public boolean closeDB() {
        return true;
    }

    @Override
    public boolean openTransaction() {
        return true;
    }

    @Override
    public boolean closeTransaction() {
        return true;
    }

    @Override
    public long getNumberOfNodes() {
        return 100;
    }

    @Override
    public long getNumberOfEdges() {
        return 100;
    }

    @Override
    public long getDBsize() {
        return 100;
    }

    @Override
    public boolean insertPerson(long _pid, String _name, String _age, String _location) {
        return true;
    }

    @Override
    public boolean insertWebPage(long _wpid, String _url, String _creation) {
        return true;
    }

    @Override
    public boolean insertFriend(long id_person1, long id_person2) {
        return true;
    }

    @Override
    public boolean insertLike(long id_person, long id_webpage) {
        return true;
    }

    @Override
    public long Q1(String person_name) {
        return 0;
    }

    @Override
    public long Q2(long webpage_id) {
        return 0;
    }

    @Override
    public long Q3(long person_id) {
        return 0;
    }

    @Override
    public String Q4(long person_id) {
        return "";
    }

    @Override
    public long Q5(long person_id) {
        return 0;
    }

    @Override
    public long Q6(long person_id) {
        return 0;
    }

    @Override
    public long Q7(long person_id) {
        return 0;
    }

    @Override
    public boolean Q8(long person_id1, long person_id2) {
        return true;
    }

    @Override
    public long Q9(long person_id1, long person_id2) {
        return 0;
    }

    @Override
    public long Q10(long person_id1, long person_id2) {
        return 0;
    }

    @Override
    public long Q11(long person_id1, long person_id2) {
        return 0;
    }

    @Override
    public long Q12(long person_id1) {
        return 0;
    }
      @Override
    public long Q13(long person_id1) {
        return 0;
    }

    @Override
    long getNumberOfWebPages() {
        return 100;
    }

    @Override
    long getNumberOfPersons() {
        return 100;
    }

    
}
