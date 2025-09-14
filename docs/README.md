# 🤖 DiHeng – Your Friendly Task Companion

Welcome to **DiHeng**, a playful and helpful chatbot designed to help you manage your tasks with ease while keeping the
experience fun and interactive!

DiHeng comes with a personality: expressive emojis, witty messages, and helpful guidance. Let’s make task management
enjoyable!

---

## 🌟 Features

- **Add Tasks**
    - `todo <description>` – Add a simple to-do item.
    - `deadline <description> /by <dd/MM/yyyy HH:mm>` – Add a task with a deadline.
    - `event <description> /from <start> /to <end>` – Add an event with start and end times.

- **View Tasks**
    - `list` – See all your tasks with current status and deadlines.

- **Update Task Status**
    - `mark <task index>` – Mark a task as done ✅.
    - `unmark <task index>` – Mark a task as not done 🔴.

- **Remove Tasks**
    - `delete <task index>` – Remove a task 🗑️.
    - `clear` – Remove all tasks and start fresh 🎉.

- **Find Tasks**
    - `find <keyword>` – Search for tasks by keyword 🔍.

- **Load/Change Filepath**
    - `load <filepath>` – Change the location of your task storage file.

- **Help**
    - `help` – Displays a list of supported commands with guidance.

- **Exit**
    - `bye` – Quit DiHeng 👋.

---

## 🚀 Quick Start

1. **Launch the GUI**
    - Open `MainWindow.java` or your compiled JAR file.
    - You’ll see a chat interface with a text input and send button.

2. **Set Up DiHeng**
    - If not automatically set, link the chatbot using:
      ```java
      mainWindow.setChatbot(new DiHeng());
      ```

3. **Start Adding Tasks**
    - Type commands in the input field, press **Enter** or click **Send**.

4. **Interact with DiHeng**
    - DiHeng will respond with fun emojis and helpful feedback.
    - Example:
      ```
      User: todo Buy groceries
      DiHeng: ⭐ Got it! Added this task: [T][ ] Buy groceries
      ```

5. **Enjoy Personality Features**
    - Emojis for task status: ✅, 🔴, 🗑️, 🎉
    - Witty, friendly messages for feedback and reminders

---

## 💡 Tips & Tricks

- **Multiple Task Operations:**
    - You can mark/unmark multiple tasks at once:
      ```
      mark 1 2 4
      ```
- **Keep Inputs Clean:**
    - Commands are case-insensitive but avoid unnecessary spaces.
- **Emojis for Fun:**
    - DiHeng uses emojis to give instant visual feedback. Don’t be surprised when it celebrates your progress 🎉.

---

## 🛠 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/diheng.git
