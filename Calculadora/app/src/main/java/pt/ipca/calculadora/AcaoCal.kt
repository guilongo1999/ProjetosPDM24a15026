package pt.ipca.calculadora

sealed class AcaoCalc {

    data class Number(val number: Int): AcaoCalc()
    object Clear: AcaoCalc()
    object Delete: AcaoCalc()
    object Decimal: AcaoCalc()
    object Calculate: AcaoCalc()
    //object Square: AcaoCalc()
    data class Operacao(val operacao: OperacaoCalc) : AcaoCalc()




}
