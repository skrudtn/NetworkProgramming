package Model;

/**
 * Created by skrud on 2017-10-25.
 */

/**
 *  Server Ack Class
 */
public class Ack {
    public final static int error = 0; // error
    public final static int lAck = 1; // login 성공 ack
    public final static int pREJ = 2; // pw 틀림 rej
    public final static int iREJ = 3; // id 틀림 rej
    public final static int oACK = 4; // 아이디 생성 가능 ack
    public final static int oREJ = 5; // 아이디 중복 rej
    public final static int sACK = 6; // 가입 성공 akc
    public final static int sREJ = 7; // 가입 실패 rej
    public final static int pwOACK = 8; // 아이디 생성 가능 ack
    public final static int pwOREJ = 9; // 아이디 중복 rej
    public final static int pwCACK = 10; // 아이디 생성 가능 ack
    public final static int pwCREJ = 11; // 아이디 중복 rej
    public final static int insertACK= 12; // db 저장 성공 ack
    public final static int insertREJ= 13; // db 저장 실패 rej

}
