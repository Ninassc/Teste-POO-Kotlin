data class Livro(val titulo: String, val autor: String, val isbn: String, var disponivel: Boolean)

data class Usuario(val nome: String, val id: Int, val livrosEmprestados: MutableList<Livro>)

data class Biblioteca(val acervo: MutableList<Livro>, val usuarios: MutableList<Usuario>)

fun leituraSegura() = readLine() ?: error("Valor nulo inválido!")

fun lerUsuario(usuario: MutableList<Usuario>): Usuario {
    println("Digite o NOME do usuário: ")
    val nome = leituraSegura()

    val id = usuario.size + 1

    var quanteLivros = 0

    do {
        println("Quantos livros deseja emprestar? ")
        quanteLivros = leituraSegura().toInt()
    } while (quanteLivros > 3)

    var livrosEmprestados = mutableListOf<Livro>()
    var cont = quanteLivros

    while (cont > 0) {
        println("Título do Livro: ")
        val titulo = leituraSegura()

        println("Autor do Livro: ")
        val autor = leituraSegura()

        var isbn: String
        do {
            println("Isbn do Livro: ")
            isbn = leituraSegura()
        } while (isbn.length != 13)

        var estaDisponivel: Boolean
        do {
            println("Disponibilidade: (1 para DISPONÍVEL/ 2 para INDISPONÍVEL) --> ")
            val disponivel = leituraSegura().toInt()

            if (disponivel == 1) {
                estaDisponivel = true
            } else {
                estaDisponivel = false
            }
        } while (disponivel != 1 && disponivel != 2)

        val livro = Livro(titulo, autor, isbn, estaDisponivel)

        livrosEmprestados.add(livro)
        cont -= 1
    }

    val usuario = Usuario(nome, id, livrosEmprestados)
    return usuario
    //println(nome)
    //println(quanteLivros)

//    for (livro in livrosEmprestados) {
//        println(livro.titulo)
//    }

}

fun main() {
    var livros: MutableList<Livro> = mutableListOf(
        Livro("Peter Pan", "JM Berrie", "1234567891234", true),
        Livro("Jogos Vorazes", "JM Berrie", "1174546891639", false),
        Livro("A Metarmofose", "Franz Kafka", "1224543811679", true),
    )

    var usuarios: MutableList<Usuario> = mutableListOf(
        Usuario(
            "Pedro", 1,
            mutableListOf(
                Livro("Peter Pan", "JM Berrie", "1234567891234", true),
                Livro("Jogos Vorazes", "JM Berrie", "1174546891639", false),
                Livro("A Metarmofose", "Franz Kafka", "1224543811679", true)
            )
        )
    )

    while (true) {
        println("Escolha a ação: ")
        println("0 para SAIR\n1 para ADICIONAR USUÁRIO\n2 para CADASTRAR LIVROS\n--> ")
        var acao = readLine()!!.toInt()

        if (acao == 0) {
            break
        } else if (acao == 1) {
            var usuario = lerUsuario(usuarios)
            usuarios.add(usuario)
            for (livro in usuario.livrosEmprestados){
                livros.add(livro)
            }
        }
    }

    var biblioteca = Biblioteca(livros, usuarios)
    for (livro in biblioteca.acervo){
        println(livro.titulo)
    }
}