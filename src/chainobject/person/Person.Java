package chainobject.person;

class Person extends ChainObject{
    protected static final String SRC_PATH = super.SRC_PATH + DataProp
            .read(Paths.get(DataProp.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("PersonChainObjectSrcFolder");
    
    private String name;
    private String foreName;
    private boolean isVerified;
    public Person(String hash, PublicKey publicKey, PrivateKey privateKey,String name, String foreName,boolean isVerified ){
        this.name = name;
        this.foreName = foreName;
        this.isVerified = isVerified;
    }
    



}