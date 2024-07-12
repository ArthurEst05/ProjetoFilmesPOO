package entites;

public class Comentario {
    private Usuario usuario;
    private String texto;

    public Comentario(Usuario usuario, String texto) {
        this.usuario = usuario;
        this.texto = texto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return usuario.getNome() + ": " + texto;
    }
}
