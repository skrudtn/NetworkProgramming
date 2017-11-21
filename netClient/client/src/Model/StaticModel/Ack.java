package Model.StaticModel;

/**
 * Created by skrud on 2017-10-25.
 */
/**
 *  Client Ack Class
 */
public class Ack {
    public final static int error = 0; // error
    public final static int lAck = 1; // login 성공 ack
    public final static int pREJ = 2; // pw 틀림 rej
    public final static int iREJ = 3; // id 틀림 rej
    public final static int oACK = 4; // 아이디 생성 가능 ack
    public final static int oREJ = 5; // 아이디 중복 rej
    public final static int signUpACK = 6; // 가입 성공 akc
    public final static int signUpREJ = 7; // 가입 실패 rej
    public final static int pwOACK = 8; // 아이디 생성 가능 ack
    public final static int pwOREJ = 9; // 아이디 중복 rej
    public final static int pwCACK = 10; // 비번 변경 ack
    public final static int pwCREJ = 11; // 비번 변경 rej
    public final static int pushACK = 12; // db 저장 성공 ack
    public final static int pushREJ = 13; // db 저장 실패 rej
    public final static int cloneACK = 14; // clone ack
    public final static int cloneREJ = 15; // clone reject
    public final static int searchAck = 16; // search ack
    public final static int searchRej = 17; // search reject
    public final static int loginDupliAck = 18; // 중복 로그인 ack
    public final static int logoutAck= 19; // log out ack
    public final static int searchIdAck = 20; // id search ack
    public final static int searchIdRej= 21; // id search rej
    public final static int addFriendAck = 22; // add friend ack
    public final static int addFriendRej= 23; // add friend rej
    public final static int overlapRepoACK = 24; // repo 생성 가능 ack
    public final static int overlapRepoREJ = 25; // repo 생성 실패 rej
    public final static int repoACK = 26; // repo 전송 가능 ack
    public final static int repoREJ = 27; // repo 전송 불가 rej
    public final static int versionACK = 28; // version 선택 가능 ack
    public final static int versionREJ = 29; // version 선택 실패 rej
}
