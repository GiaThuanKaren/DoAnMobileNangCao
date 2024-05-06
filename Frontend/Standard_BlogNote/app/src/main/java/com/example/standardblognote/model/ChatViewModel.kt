package com.example.standardblognote.model
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import  androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardblognote.data.ChatState
import com.example.standardblognote.data.FCmApi
import com.example.standardblognote.data.NofiticationBody
import com.example.standardblognote.data.SendMessageDto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.IOException


class ChatViewModel:ViewModel() {
    var state by mutableStateOf(
        ChatState()
    )
        private set



    private val api:FCmApi = Retrofit.Builder().baseUrl("http://192.168.1.4:5000").addConverterFactory(
        MoshiConverterFactory.create()
    ).build().create();

    fun onRemoteTokenChange(newToken:String){
        state = state.copy(
            remoteToken = newToken
        )
    }
    fun onSubmitRemoteToken(){
        state = state.copy(
            isEnteringToken = false
        )
    }


    fun onMessageChnage (message:String){
        state = state.copy(
            messageText = message
        )
    }

    fun sendMessage (isBroadCast:Boolean){
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to= if (isBroadCast) null else state.remoteToken,
                nofication = NofiticationBody(
                    title = "New Message",
                    body = state.messageText
                )
            )

            try {
                if ( isBroadCast){
                    api.broadcast(messageDto)
                }else{
                    api.sendMessage(messageDto)
                }

                state= state.copy(
                    messageText = ""
                )
            }catch (e:HttpException){
                e.printStackTrace()
            }catch (e:IOException){
                e.printStackTrace()
            }

        }
    }

}