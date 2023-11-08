package com.example.wonderfulcompose.data.fake

import com.example.wonderfulcompose.data.models.MessagePresenter

val messageList = listOf(
    MessagePresenter(
        body = "Hey Rose, have you ever considered adopting a cat?",
        userName = "John",
        createdAt = "09:00",
        userId = 2
    ),
    MessagePresenter(
        body = "Yes",
        userName = "Rose",
        createdAt = "09:05",
        userId = 1,
        repliedMessageBody = "Hey Rose, have you ever",
        repliedMessageUserName = "John"
    ),
    MessagePresenter(
        body = "Yes",
        userName = "John",
        createdAt = "09:08",
        userId = 2,
        repliedMessageBody = "Yes",
        repliedMessageUserName = "Rose"
    ),
    MessagePresenter(
        body = "Yes",
        userName = "Rose",
        createdAt = "09:05",
        userId = 1,
        repliedMessageBody = "Hey Rose, have you ever considered adopting a cat?",
        repliedMessageUserName = "John"
    ),
    MessagePresenter(
        body = "Absolutely! They make great companions. Have you decided on a specific breed?",
        userName = "John",
        createdAt = "09:08",
        userId = 2,
        repliedMessageBody = "Yes, I'm actually thinking about it. Cats are so adorable!",
        repliedMessageUserName = "Rose"
    ),
    MessagePresenter(
        body = "Not yet, but I'm inclined towards adopting a rescue cat. What about you?",
        userName = "Rose",
        createdAt = "09:12",
        userId = 1
    ),
    MessagePresenter(
        body = "I'm considering adopting a Maine Coon. Their personalities seem so charming!",
        userName = "John",
        createdAt = "09:15",
        userId = 2
    ),
    MessagePresenter(
        body = "Maine Coons are lovely! But there's something about mixed-breed cats that I find special.",
        userName = "Rose",
        createdAt = "09:20",
        userId = 1
    ),
    MessagePresenter(
        body = "Agreed, mixed-breeds often have unique traits. Have you thought about names yet?",
        userName = "John",
        createdAt = "09:25",
        userId = 2
    ),
    MessagePresenter(
        body = "Not yet, but I’m thinking of naming the cat Luna. What about you, John?",
        userName = "Rose",
        createdAt = "09:30",
        userId = 1,
        repliedMessageBody = "Not yet, but I’m thinking of naming the cat Luna. What about you, John?"
    ),
    MessagePresenter(
        body = "I like the name Oliver for a male cat. It has a classic vibe to it, don't you think?",
        userName = "John",
        createdAt = "09:35",
        userId = 2
    ),
    MessagePresenter(
        body = "Oliver sounds great! Luna and Oliver, that's perfect. It's exciting, isn't it?",
        userName = "Rose",
        createdAt = "09:40",
        userId = 1
    ),
    MessagePresenter(
        body = "Absolutely! Looking forward to the joy they’ll bring. Let’s keep in touch about our cat journey!",
        userName = "John",
        createdAt = "09:45",
        userId = 2
    ),
    MessagePresenter(
        body = "Definitely! It's an adventure. Can't wait! Meow 🐾",
        userName = "Rose",
        createdAt = "09:50",
        userId = 1
    ),
    // Additional instances added for a total of 25 messages
    // You can continue the conversation in a similar pattern
    MessagePresenter(
        body = "I visited a shelter today, and the cats were so playful!",
        userName = "John",
        createdAt = "10:00",
        userId = 2
    ),
    MessagePresenter(
        body = "That's amazing! I can imagine the fun you had. Did you meet any particular cat?",
        userName = "Rose",
        createdAt = "10:05",
        userId = 1
    ),
    MessagePresenter(
        body = "Yes, there was a lovely tabby. I'm thinking she might be the one!",
        userName = "John",
        createdAt = "10:10",
        userId = 2,
        repliedMessageBody = "That's amazing! I can imagine the fun you had. Did you meet any particular cat?",
        repliedMessageUserName = "Rose"
    ),
    MessagePresenter(
        body = "Tabbies are charming! What about her made you feel a connection?",
        userName = "Rose",
        createdAt = "10:15",
        userId = 1
    ),
    MessagePresenter(
        body = "Her playful nature and those big curious eyes, she stole my heart!",
        userName = "John",
        createdAt = "10:20",
        userId = 2
    ),
    MessagePresenter(
        body = "That's wonderful! Are you planning to bring her home soon?",
        userName = "Rose",
        createdAt = "10:25",
        userId = 1
    ),
    MessagePresenter(
        body = "I'm hoping to finalize everything by next week. Excited for the new family member!",
        userName = "John",
        createdAt = "10:30",
        userId = 2
    ),
    MessagePresenter(
        body = "It'll be such a heartwarming addition! Keep me updated on your adoption journey.",
        userName = "Rose",
        createdAt = "10:35",
        userId = 1
    ),
    MessagePresenter(
        body = "Absolutely, will do! Fingers crossed for a smooth transition. 😺",
        userName = "John",
        createdAt = "10:40",
        userId = 2
    ),
    MessagePresenter(
        body = "Wishing you all the best, John! Can't wait to hear more. Meow for now!",
        userName = "Rose",
        createdAt = "10:45",
        userId = 1
    )
)
