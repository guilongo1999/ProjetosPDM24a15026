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

    fun onAction(action: AcaoCalc) {


        when(action) {

            is AcaoCalc.Number -> enterNumber(action.number)
            is AcaoCalc.Decimal -> enterDecimal()
            is AcaoCalc.Clear -> state = EstadoCalc()
            is AcaoCalc.Operacao -> enterOperation(action.operacao)
            is AcaoCalc.Calculate -> performCalculation()
            is AcaoCalc.Delete -> performDeletion()
            //is OperacaoCalc.Square -> sqrt(number1)
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
                is OperacaoCalc.Square -> sqrt(number1)
                null -> return

            }

            state = state.copy(number1 = result.toString().take(15), number2 = "", operacao = null)
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


            state = state.copy(number1 = state.number2 + ".")



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

    companion object {

       private const val MAX_NUM_LENGTH = 8
    }
}

