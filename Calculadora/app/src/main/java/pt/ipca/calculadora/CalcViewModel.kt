package pt.ipca.calculadora

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.nio.file.Files.delete
import kotlin.math.sqrt

class CalcViewModel: ViewModel() {

    var state by mutableStateOf(EstadoCalc())
        private set

    var isOn by mutableStateOf(false)
        private set

    var changeSignal by mutableStateOf(false)
        private set

    private var memory: Double = 0.0

    fun onAction(action: AcaoCalc) {
        if (!isOn && action !is AcaoCalc.On) return

        when(action) {

            is AcaoCalc.Number -> enterNumber(action.number)
            is AcaoCalc.Decimal -> enterDecimal()
            is AcaoCalc.Clear -> state = EstadoCalc()
            is AcaoCalc.Operacao -> enterOperation(action.operacao)
            is AcaoCalc.Calculate -> performCalculation()
            is AcaoCalc.Delete -> performDeletion()
            //is OperacaoCalc.Square -> sqrt(number1)
            is AcaoCalc.On -> TurnOn()
            is AcaoCalc.Percent -> PerformPercent()
            is AcaoCalc.ChangeSignal -> ChangeSignal()
            is AcaoCalc.AddMemory -> AddMem()
            is AcaoCalc.RemoveMemory -> RemoveMem()
            is AcaoCalc.Memory -> Mem()
            null -> return
        }
    }

    private fun performDeletion() {

        when { state.number2.isNotBlank() -> state.copy(number2 = state.number2.dropLast(1)
        )
        state.operacao != null  -> state = state.copy(operacao = null)

        state.number1.isNotBlank() -> state = state.copy(number1 = state.number1.dropLast(1))
        }

    }

    private fun performCalculation() {

       val number1  = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        if(number1 != null && number2 != null) {

        val result = when(state.operacao) {


                is OperacaoCalc.Add -> number1+number2
                is OperacaoCalc.Subtract -> number1-number2
                is OperacaoCalc.Multiply -> number1 * number2
                is OperacaoCalc.Divide -> number1 / number2
                is OperacaoCalc.Square -> number1 * sqrt(number1)
                null -> return

            }

            state = state.copy(number1 = result.toString().take(5), number2 = "", operacao = null)
        }

    }

    private fun enterOperation(operacao: OperacaoCalc) {

    if(state.number1.isNotBlank()) {

        state = state.copy(operacao = operacao)
    }

    }

    private fun enterDecimal() {

        if(state.operacao == null && !state.number1.contains(".") && state.number1.isNotBlank()) {


            state = state.copy(number1 = state.number1 + ".")

            return

        }

        if(!state.number2.contains(".") && state.number2.isNotBlank()) {


            state = state.copy(number2 = state.number2 + ".")



        }

    }



    private fun enterNumber(number: Int) {

        if(state.operacao == null) {

            if(state.number1.length >= MAX_NUM_LENGTH) {

                return
            }

            state = state.copy(number1 = state.number1 + number)

            return
        }

        if(state.number2.length >= MAX_NUM_LENGTH) {

            return
        }

        state = state.copy(number2 = state.number2 + number)

    }

    private fun TurnOn() {

        if(!isOn) {

            isOn = true
            state = EstadoCalc()

        }

        else {

            isOn = false
            state = EstadoCalc()
        }


    }

    private fun PerformPercent() {

        //val count
        //val totalCount
        //var percentage = (count.toDouble() / totalCount.toDouble()) * 100.0

        val number = state.number1.toDoubleOrNull()
        if (number != null) {
            state = state.copy(number1 = (number / 100).toString())
        }



    }


    private fun ChangeSignal() {
        if (state.operacao == null) {
            // Change signal of number1
            state = if (state.number1.startsWith("-")) {
                state.copy(number1 = state.number1.removePrefix("-"))
            } else {
                state.copy(number1 = "-${state.number1}")
            }
        } else {
            // Change signal of number2
            state = if (state.number2.startsWith("-")) {
                state.copy(number2 = state.number2.removePrefix("-"))
            } else {
                state.copy(number2 = "-${state.number2}")
            }
        }
    }

    private fun AddMem(): Double {

        val currentNumber = state.number1.toDoubleOrNull()
        if (currentNumber != null) {
            memory += currentNumber
        }
        return memory

    }

    private fun RemoveMem(): Double {


        val currentNumber = state.number1.toDoubleOrNull()
        if (currentNumber != null) {
            memory -= currentNumber
        }
        return memory

    }

    private fun Mem() {

        state = state.copy(number1 = memory.toString())


    }

    companion object {

       private const val MAX_NUM_LENGTH = 8
    }
}

