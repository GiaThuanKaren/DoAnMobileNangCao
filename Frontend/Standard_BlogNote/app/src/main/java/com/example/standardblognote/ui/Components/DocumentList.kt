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
import androidx.navigation.NavController
import com.example.standardblognote.R
import com.example.standardblognote.model.DocumentResponseModel
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


data class ItemData(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val coverImageLink: String,
    val parentId: String?
)

@Composable
fun DocumentListStream(
    onDocument: (String) -> Unit = {},
    navController: NavController,
    documentList: DocumentList? = DocumentList()
) {
//    val (parentDocumentId, level) = documentList
    var expanded by remember {
        mutableStateOf<MutableMap<String, Boolean>>(mutableMapOf())
    }

    var documents by remember {
        mutableStateOf(emptyList<ItemData>())
    }

    var apiDocuments by remember {
        mutableStateOf(listOf<DocumentResponseModel>())
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            val res = try {
                val parentDocumentId = documentList?.parentDocumentId ?: "0"
                RetrofitInstance.api.GetAllParentDocumentId(parentDocumentId)
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
//                            && response.msg == 200
                    if (response != null) {
                        apiDocuments = response.data!!
                        Log.i("STANDARDs", "${apiDocuments}")
                    }

                }
            }
        }
    }


    documents = listOf(
        ItemData(
            id = "1",
            title = "Unofficial Notion Design System v1.1",
            description = "string",
            icon = "",
            coverImageLink = "string",
            parentId = null
        ),
        ItemData(
            id = "2",
            title = "Apple Design Resources - iOS 17 and IPadOS 17",
            description = "string",
            icon = "",
            coverImageLink = "string",
            parentId = "1"
        ),
        ItemData(
            id = "3",
            title = "SALY - 3D Illustration Pack",
            description = "string",
            icon = "",
            coverImageLink = "string",
            parentId = null
        ),
        ItemData(
            id = "4",
            title = "coolicons | Free Iconset",
            description = "string 123",
            icon = "",
            coverImageLink = "string3443",
            parentId = "1"
        )
    )

    var document: List<ItemData> = emptyList()
    for (d in documents) {
        if (d.parentId == documentList?.parentDocumentId) {
            document = document + d
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

    if (documentList?.level == 0) {
        // Không hiển thị văn bản "No page inside"
    } else if (documentList?.level ?: 0 > 0 && expanded.isEmpty()) {
        Log.i("Expandeds", "${expanded.isEmpty()}")
        Text(
            text = "No page inside",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500)),
            modifier = Modifier
                .padding(start = documentList?.level?.let { ((it * 12) + 25).dp } ?: 0.dp, top = 7.dp)
        )
    }

    apiDocuments.let { documentLs ->
        documentLs.forEach { documentL ->
            key(documentL.id) {
                ItemDocument(
                    Item(
                        id = documentL.id,
                        documentIcon = documentL.icon,
                        active = true,
                        expanded = expanded[documentL.id],
                        isSearch = false,
                        level = documentList?.level,
                        onExpand = { onExpanded(documentL.id)  },
                        label = documentL.title,
                        onClick = { onDocument?.invoke(documentL.id) },
                        icon = R.drawable.file,
                    )
                )
            }
            if (expanded[documentL.id] == true) {
                DocumentListStream(
                    onDocument = {
                        navController.navigate("document/${documentL.id}")
                    },
                    navController,
                    DocumentList(
                        parentDocumentId = documentL.id,
                        level = documentList?.level?.plus(1)
                    )
                )
            }
        }
    }
}



















