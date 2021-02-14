package capaDominio.usuario;


public class Usuario
{
    private String name;
    private String password;
    
    public Usuario(String name, String plain) {
        this.name = name;
        this.password = encrypt(plain);
    }
    
    
    /** 
     * @param plainPassword
     * @return String
     */
    //encrypt v0
    public static String encrypt(String plainPassword){
        char [] chars = plainPassword.toCharArray();

        for (int i = 0; i < chars.length; i++){
            chars[i] += 35 + chars[((int)chars[i])%chars.length];
            if (chars[i] == ',') chars[i]+=1;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++){
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param plainPassword
     * @return boolean
     */
    public boolean comparePassword (String plainPassword) {
        return password.equals(encrypt(plainPassword));
    }

}