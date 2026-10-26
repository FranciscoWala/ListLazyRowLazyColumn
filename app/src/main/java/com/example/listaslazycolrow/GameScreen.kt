package com.example.listaslazycolrow

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.listaslazycolrow.model.Game
import com.example.listaslazycolrow.repository.getAllGames
import com.example.listaslazycolrow.repository.getGamesByStudio

@Composable
fun GameScreen (modifier: Modifier = Modifier) {

    var studio by remember {
        mutableStateOf("")
    }

    var gamesListByStudio by remember {
        mutableStateOf(getGamesByStudio(""))
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Meus jogos favoritos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = studio,
            onValueChange = {studio = it},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Nome do estúdio"
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        gamesListByStudio = getGamesByStudio(studio)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            },
            singleLine = true
        )

        LazyRow () {
            items(gamesListByStudio) {
                StudioCard(game = it)
            }
        }

        LazyColumn() {
            items(gamesListByStudio) {
                GameCard(it)
            }
        }
    }
}


//@Composable
//fun GameCard(game: Game) {
//    Card(modifier = Modifier.padding(bottom = 8.dp)) {
//        Row( modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column( modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .weight(3f)
//            ) {
//                Text(
//                    text = game.title,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Text(
//                    text = game.studio,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            }
//
//            Text(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth(),
//                text = "${game.releaseYear}",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Blue
//            )
//        }
//    }
//}

@Composable
fun GameCard(game: Game) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp) // Adiciona uma margem lateral legal para a lista
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. Imagem via URL (Coil)
            AsyncImage(
                model = game.imageUrl,
                contentDescription = game.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 2. Título e Estúdio (Usando weight(1f) sem o fillMaxWidth interno)
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = game.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, // Limita a 1 linha se o nome for gigante
                    overflow = TextOverflow.Ellipsis // Adiciona "..." se o texto cortar
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = game.studio,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 3. Ano de Lançamento
            Text(
                text = "${game.releaseYear}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}


@Composable
fun StudioCard (modifier: Modifier = Modifier, game: Game) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(end = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = game.studio,
                textAlign = TextAlign.Center
            )
        }
    }
}
