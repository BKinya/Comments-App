## Comments-App

An android app to post comments. You can also see your comments.

<img src="https://github.com/BKinya/Comments-App/blob/master/img/Enter%20comments.png" width="200" alt="Enter Comments">&emsp;&emsp;<img src="https://github.com/BKinya/Comments-App/blob/master/img/comments%20list.png" width="200" alt="Comments list">

The app is implemented using MVI(Model - View - Intent) Architecture. 

#### Model
- Represents the view state.
- It is Immutable
```
sealed class CommentUiState {
    object NoComments: CommentUiState()
    object Loading: CommentUiState()
    data class Success(val data: List<Comment>): CommentUiState()
}
```

#### Intent 
- A future action that changes the state of the Model
```
sealed class CommentIntent {
    object FetchComments: CommentIntent()
    data class EditComment(val comment: Comment): CommentIntent()
    data class DeleteComment(val id: Int): CommentIntent()
}

sealed class AddCommentsIntent{
    data class SaveComment(val comment: String): AddCommentsIntent()
}
```

#### View
UI classes i.e. the fragments

