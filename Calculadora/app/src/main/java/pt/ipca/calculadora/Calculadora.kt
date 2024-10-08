package pt.ipca.calculadora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable

fun Calc(state: EstadoCalc, modifier: Modifier = Modifier, buttonSpacing: Dp = 8.dp, onAction: (AcaoCalc) -> Unit) {

    Box(modifier = modifier) {


        Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing) //ajustar depois
        ) {
            Text(
                text= state.number1 + (state.operacao?.symbol ?: "") + state.number2,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Light,
                fontSize = 80.sp,
                color = Color.White,
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing) //ajustar depois
            ) {

                BotaoCalculadoraEspecial(
                    symbol = "MRC",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),




                    onClick = {

                        onAction(AcaoCalc.Clear)

                    }

                )

                BotaoCalculadora(
                    symbol = "M-",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Delete)

                    }

                )

                BotaoCalculadora(
                    symbol = "M+",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Operacao(OperacaoCalc.Divide))

                    }

                )

                BotaoCalculadoraEspecial(
                    symbol = "ON/C",
                    modifier = Modifier
                        .background(Orange)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Clear)

                    }

                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing) //ajustar depois
            ) {

                BotaoCalculadora(
                    symbol = "√ ",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Operacao(OperacaoCalc.Square))

                    }

                )

                BotaoCalculadora(
                    symbol = "%",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Delete)

                    }

                )

                BotaoCalculadora(
                    symbol = "+/-",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Operacao(OperacaoCalc.Divide))

                    }

                )

                BotaoCalculadora(
                    symbol = "CE",
                    modifier = Modifier
                        .background(Orange)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Clear)

                    }

                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing) //ajustar depois
            ) {

                BotaoCalculadora(
                    symbol = "7",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(7))

                    }

                )

                BotaoCalculadora(
                    symbol = "8",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(8))

                    }

                )

                BotaoCalculadora(
                    symbol = "9",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(9))

                    }

                )

                BotaoCalculadora(
                    symbol = "/",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Operacao(OperacaoCalc.Divide))

                    }

                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing) //ajustar depois
            ) {

                BotaoCalculadora(
                    symbol = "4",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(4))

                    }

                )

                BotaoCalculadora(
                    symbol = "5",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(5))

                    }

                )

                BotaoCalculadora(
                    symbol = "6",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(6))

                    }

                )

                BotaoCalculadora(
                    symbol = "*",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Operacao(OperacaoCalc.Multiply))

                    }

                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing) //ajustar depois
            ) {

                BotaoCalculadora(
                    symbol = "1",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(1))

                    }

                )

                BotaoCalculadora(
                    symbol = "2",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(2))

                    }

                )

                BotaoCalculadora(
                    symbol = "3",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(3))

                    }

                )

                BotaoCalculadora(
                    symbol = "-",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Operacao(OperacaoCalc.Subtract))

                    }

                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing) //ajustar depois
            ) {

                BotaoCalculadora(
                    symbol = "0",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Number(0))

                    }

                )

                BotaoCalculadora(
                    symbol = ".",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Decimal)

                    }

                )

                BotaoCalculadora(
                    symbol = "=",
                    modifier = Modifier
                        .background(Grey)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Calculate)

                    }



                )

                BotaoCalculadora(
                    symbol = "+",
                    modifier = Modifier
                        .background(Black)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {

                        onAction(AcaoCalc.Operacao(OperacaoCalc.Add))

                    }



                )



            }

        }

    }

}