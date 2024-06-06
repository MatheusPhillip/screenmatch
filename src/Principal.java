public class Principal {
    public static void main(String[] args) {
        Filme meuFilme = new Filme();
        meuFilme.nome = "O poderoso chefão";
        meuFilme.anoDeLancamento = 1970;
        meuFilme.duracaoEmMinutos = 180;
        meuFilme.avaliacao = 8.5;
        meuFilme.incluidoNoPlano = true;

        System.out.println(meuFilme.nome);
        System.out.println(meuFilme.anoDeLancamento);
    }
}