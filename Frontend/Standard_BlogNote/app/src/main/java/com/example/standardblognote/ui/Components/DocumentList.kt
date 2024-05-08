 package com.example.standardblognote.ui.Components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.model.DocumentResponseModel
import com.example.standardblognote.model.DocumentUserModel
import com.example.standardblognote.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

data class DocumentList(
    val parentDocumentId: String? = null,
    val level: Int? = 0,
)

@Composable
fun DocumentListStream(
    onDocument: (String) -> Unit = {},
    uid: String,
    navController: NavHostController,
    documentList: DocumentList? = DocumentList(),
) {
    var expanded by remember {
        mutableStateOf<MutableMap<String, Boolean>>(mutableMapOf())
    }
    var apiDocuments by remember {
        mutableStateOf(listOf<DocumentUserModel>())
    }
    val parentDocumentId = documentList?.parentDocumentId ?: "-1"

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            val res = try {
                RetrofitInstance.api.GetAllParentDocumentId(uid, parentDocumentId)
            } catch (e: HttpException) {
                Log.i("INFO Api Call Fail", "${e.message()}")
                return@launch
            } catch (e: IOException) {
                Log.i("INFO Api Call Fail", "${e.message}")
                return@launch
            }

            Log.i("Call api", "${res}")

            if (res.isSuccessful && res.body() != null) {
                withContext(Dispatchers.Main) {
                    val response = res.body()!!
                    if (response != null) {
                        apiDocuments = response
                        Log.i("STANDARDs", "${apiDocuments}")
                    }

                }
            }
        }
    }

    val onExpanded: (String) -> Unit = { documentId ->
        expanded = expanded.toMutableMap().apply {
            this[documentId] = !(this[documentId] ?: false)
        }
    }

//    if (document.isEmpty()) {
//        ItemSkeleton(level = documentList?.level)
//        if (documentList?.level === 0) {
//            ItemSkeleton(level = documentList?.level)
//            ItemSkeleton(level = documentList?.level)
//        }
//    }

//    if (documentList?.level == 0) {
//        // Không hiển thị văn bản "No page inside"
//    } else if (documentList?.level ?: 0 > 0 && expanded.isEmpty()) {
//        Log.i("Expandeds", "${expanded.isEmpty()}")
//        Text(
//            text = "No page inside",
//            fontSize = 14.sp,
//            fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500)),
//            modifier = Modifier
//                .padding(start = documentList?.level?.let { ((it * 12) + 25).dp } ?: 0.dp, top = 7.dp)
//        )
//    }

    apiDocuments.let { documentLs ->
        documentLs.forEach { documentL ->
            key(documentL.listPost.id) {
                ItemDocument(
                    Item(
                        id = documentL.listPost.id,
                        uid = uid,
                        documentIcon = documentL.listPost.icon,
                        expanded = expanded[documentL.listPost.id],
                        level = documentList?.level,
                        onExpand = { onExpanded(documentL.listPost.id)  },
                        label = documentL.listPost.title,
                        onClick = { onDocument?.invoke(documentL.listPost.id) },
                        icon = R.drawable.file,
                        navController
                    ),
                )
            }
            if (expanded[documentL.listPost.id] == true) {
                DocumentListStream(
                    onDocument = { documentId ->
                        // TODO -> document/documentId/parentDocumentId
                        navController.navigate("document/${documentId}/${documentL.listPost.id}")
                    },
                    uid,
                    navController,
                    DocumentList(
                        parentDocumentId = documentL.listPost.id,
                        level = documentList?.level?.plus(1)
                    )
                )
            }
        }
    }
}



















