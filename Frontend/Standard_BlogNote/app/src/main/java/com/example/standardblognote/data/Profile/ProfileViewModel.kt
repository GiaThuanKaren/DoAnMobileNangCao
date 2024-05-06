
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardblognote.model.ProfileDetail
import com.example.standardblognote.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api

    // Sử dụng User() thay vì null để tránh gặp phải null pointer exception
    val userLiveData = mutableStateOf(ProfileDetail())



    fun fetchUser(userId: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.getUserProfile(userId)
                }
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        if (it.msg == 200) {
                            userLiveData.value = it.data
                        } else {
                            // Xử lý thông báo lỗi
                        }
                    }
                } else {
                    // Xử lý khi nhận được phản hồi không thành công
                }
            } catch (e: HttpException) {
                // Xử lý khi có lỗi HTTP
            } catch (e: IOException) {
                // Xử lý khi có lỗi IO
            }
        }
    }
}
