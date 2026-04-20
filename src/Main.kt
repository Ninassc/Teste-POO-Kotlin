import java.util.Dictionary

data class Livro(val titulo: String, val autor: String, val isbn: String, var disponivel: Boolean){
    override fun toString(): String {
        val status = if (disponivel) "disponível" else "indisponível"
        return "\"$titulo\" de $autor (ISBN: $isbn) [$status]"
    }
}

data class Usuario(val nome: String, val id: Int, val livrosEmprestados: MutableList<Livro>){
    override fun toString(): String {
        return "$nome (id: $id)"
    }

    var livrosPegos : MutableList<Livro> = mutableListOf()

    fun pegarLivro(livros : MutableList<Livro>){
        println("Título do livro: ")
        val titulo = leituraSegura()

        for(livro in livros){
            if(livro.titulo == titulo && livro.disponivel){
                livrosPegos.add(livro)
                livro.disponivel = false
            }
            if(livro.disponivel == false){
                println("Não é possível pegar o livro: $titulo --> status = Indisponível")
            }
        }

        println("Livros pegos: ")
        for (livro in livrosPegos){
            println(livro.titulo)
        }
    }
}

data class Biblioteca(val acervo: MutableList<Livro>, val usuarios: MutableList<Usuario>)

interface Emprestavel {
    fun emprestar(usuario: Usuario): Boolean

    fun devolver(): Boolean
}

//fun mostrarUsuarios(usuarios : MutableList<Usuario>){
//
//}



fun mostrarHistoricoEmprestimos(historicoEmprestimos : MutableList<MutableMap<String, Any>>){
    for (emprestimo in historicoEmprestimos){
        for(dado in emprestimo){
            println(dado.toString())
        }
    }
}

fun cadastrarLivro(): Livro {
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
    var disponivel : Int
    do {
        println("Disponibilidade: (1 para DISPONÍVEL/ 2 para INDISPONÍVEL) --> ")
        disponivel = leituraSegura().toInt()

        if (disponivel == 1) {
            estaDisponivel = true
        } else {
            estaDisponivel = false
        }
    } while (disponivel != 1 && disponivel != 2)

    val livro = Livro(titulo, autor, isbn, estaDisponivel)
    return livro
}

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
        val livro = cadastrarLivro()
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

    var historicoEmprestimos : MutableList<MutableMap<String, Any>> = mutableListOf()

    while (true) {
        println("")
        println("Escolha a ação: ")
        println("0 para SAIR\n1 para ADICIONAR USUÁRIO\n2 para CADASTRAR LIVROS\n3 para DEVOLVER LIVROS\n4 para LISTAR LIVROS DISPONÍVEIS\n5 para BUSCAR por TÍTULO ou AUTOR\n6 para MOSTRAR HISTÓRICO\n7 para PEGAR LIVRO\n--> ")

        var acao = readLine()!!.toInt()

        if (acao == 0) {
            break
        } else if (acao == 1) {
            val usuario = lerUsuario(usuarios)
            usuarios.add(usuario)
            for (livro in usuario.livrosEmprestados) {
                livros.add(livro)
            }

            var listaLivros = mutableListOf<Livro>()
            for (livro in usuario.livrosEmprestados) {
                listaLivros.add(livro)
            }

            var dicionario : MutableMap<String, Any> = mutableMapOf(
                "Usuário" to usuario,
                "Livros" to listaLivros
            )

            historicoEmprestimos.add(
               dicionario
            )

        } else if (acao == 2) {
            val livro = cadastrarLivro()
            livros.add(livro)
        }
        else if (acao == 4) {
            println("")
            println("Livros disponíveis: ")
            for (livro in livros){
                if (livro.disponivel){
                    println(livro.titulo)
                }
            }
        }
        else if(acao == 5){
            println("")
            println("Digite o NOME do autor ou TÍTULO do livro: ")
            val pesquisa = leituraSegura()

            for (livro in livros){
                if (livro.titulo == pesquisa || livro.autor == pesquisa){
                    println("Título = ${livro.titulo}")
                    println("Autor = ${livro.autor}")
                    println("Isbn = ${livro.isbn}")
                    if(livro.disponivel){
                        println("Disponível")
                    }
                    else{
                        println("Indisponível")
                    }
                }
            }
        }
        else if (acao == 6){
            mostrarHistoricoEmprestimos(historicoEmprestimos)
        }
        else if (acao == 7){
            println("SEU NOME: ")
            val nome = leituraSegura()

            for (usuario in usuarios){
                if (usuario.nome == nome){
                    usuario.pegarLivro(livros)
                }
            }
        }

        var biblioteca = Biblioteca(livros, usuarios)
        println("")
        println("Acervo da biblioteca: ")
        for (livro in biblioteca.acervo) {
            println(livro.titulo)
        }
    }
}