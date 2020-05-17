package Lab.Communication;

public class MessageFormer {
    private final String BEGINNER="_MESSAGE_BEGINNING_";
    private final String ENDER = "_MESSAGE_END_";
    byte[] message;
    boolean hasEnded;
    String line;
    public MessageFormer(String message){
        String es = BEGINNER.concat(message).concat(ENDER);
        this.message=es.getBytes();
    }
    MessageFormer(){
        hasEnded=false;
        line="";
    }
    void formFromByte(byte[] list){
        String es = new String(list);
        line=line.concat(es);
        if(line.contains(BEGINNER))
            line=line.substring(BEGINNER.length());
        if(line.contains(ENDER)){
            line=line.substring(0,line.indexOf(ENDER));
            hasEnded=true;
        }
    }
}
