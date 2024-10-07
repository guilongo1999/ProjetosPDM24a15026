package pt.ipca.calculadora

sealed class OperacaoCalc(val symbol: String){

    object Add: OperacaoCalc("+")
    object Subtract: OperacaoCalc("-")
    object Multiply: OperacaoCalc("*")
    object Divide: OperacaoCalc("/")


}