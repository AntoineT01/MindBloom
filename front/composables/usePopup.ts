// composables/usePopup.ts
import { ref } from 'vue'

export const usePopup = () => {
    const isOpen = ref(false)
    const message = ref<string | null>(null)
    const title = ref<string | null>(null)

    const showPopup = (newMessage: string, newTitle: string = 'Message') => {
        message.value = newMessage
        title.value = newTitle
        isOpen.value = true
    }

    const closePopup = () => {
        isOpen.value = false
        message.value = null
        title.value = null
    }

    return {
        isOpen,
        message,
        title,
        showPopup,
        closePopup
    }
}