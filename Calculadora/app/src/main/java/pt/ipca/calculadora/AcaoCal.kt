package pt.ipca.calculadora

sealed class AcaoCalc {

    data class Number(val number: Int): AcaoCalc()
    object Clear: AcaoCalc()
    object Delete: AcaoCalc()
    object Decimal: AcaoCalc()
    object Calculate: AcaoCalc()

    object On: AcaoCalc()

    object Percent: AcaoCalc()

    object ChangeSignal: AcaoCalc()

    object AddMemory: AcaoCalc()

    object RemoveMemory: AcaoCalc()

    object Memory: AcaoCalc()



    //object Square: AcaoCalc()
    data class Operacao(val operacao: OperacaoCalc) : AcaoCalc()




}
