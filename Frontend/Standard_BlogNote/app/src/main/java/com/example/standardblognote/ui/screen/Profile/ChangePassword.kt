
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.standardblognote.data.home.HomeViewModel

@Composable
fun ChangePassword(navController: NavHostController, viewModel: HomeViewModel) {
    // Khai báo state cho việc hiển thị Dialog
    var showDialog by remember { mutableStateOf(false) }
    var showDialogChangeTrue by remember { mutableStateOf(false) }


    // Khai báo state cho các trường nhập mật khẩu mới và xác nhận mật khẩu mới
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val profileViewModel: ProfileViewModel = viewModel()
    val context = LocalContext.current

    // Lấy UID từ SharedPreferences
    val uid = viewModel.getUidFromSharedPreferences() ?: ""
    if (uid == "") {
        LaunchedEffect(Unit) {
            viewModel.fetchUidLogin()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUserData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Các trường nhập mật khẩu mới và xác nhận mật khẩu mới
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Tên Người Dùng") },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Mật khẩu mới") },
            visualTransformation = PasswordVisualTransformation(), // Ẩn mật khẩu
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Xác nhận mật khẩu mới") },
            visualTransformation = PasswordVisualTransformation(), // Ẩn mật khẩu

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        // Nút thực hiện đổi mật khẩu
        Button(
            onClick = {
                Log.i("Get UID sadsadas", "$newPassword")
                Log.i("Get UID sadsadas", "$confirmPassword")
                if (newPassword == confirmPassword) {
                    val click = profileViewModel.changePassword(
                        id = uid,
                        newPassword = newPassword,
                        username = username,
                        Confirmpassword = confirmPassword
                    )
                    showDialogChangeTrue=true;
                } else {
                    showDialog = true // Hiển thị Dialog
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Đổi mật khẩu")
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            ChangePasswordButton(onClick = { navController.popBackStack() }, text = "Quay lại trang chủ")
        }

        // Hiển thị Dialog khi mật khẩu không khớp
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Mật khẩu không khớp",
                        fontSize = 20.sp
                    )
                },
                text = {
                    Text(
                        text = "Vui lòng nhập lại mật khẩu và xác nhận mật khẩu giống nhau.",
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    // Không cần làm gì cả
                }
            )
        }
        if (showDialogChangeTrue) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Thông báo đổi mật Khẩu",
                        fontSize = 20.sp
                    )
                },
                text = {
                    Text(
                        text = "Đổi Mật Khẩu Thành công và Tên thành công",
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    ChangePasswordButton(onClick = { navController.popBackStack() }, text = "Ok")

                }
            )
        }
    }
}

@Composable
fun ChangePasswordButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF3399FF)),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
