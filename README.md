## Comments-App

An android app to post comments. You can also see your comments.

<img src="https://github.com/BKinya/Comments-App/blob/master/img/add-comment.png" width="200" alt="Enter Comments">&emsp;&emsp;<img src="https://github.com/BKinya/Comments-App/blob/master/img/comments-lits%20.png" width="200" alt="Comments list">

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


### Resources
- [MVI - Another Member of MV* Band](https://proandroiddev.com/mvi-a-new-member-of-the-mv-band-6f7f0d23bc8a)
- [Redux style State Management for Android Apps](https://betterprogramming.pub/redux-style-state-management-for-android-apps-62da15dc7578)

