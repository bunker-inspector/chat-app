defmodule ChatServerWeb.RoomChannel do
  use ChatServerWeb, :channel

  def join(channel_name, %{"username" => username}, socket) do
    socket = socket
    |> assign(:channel, channel_name)
    |> assign(:username, username)

    {:ok, %{channel: channel_name}, socket}
  end

  def handle_in("message:add", %{"message" => message}, socket) do
    channel = socket.assigns.channel
    username = socket.assigns.username
    broadcast!(socket, "#{channel}:new_message", %{user: username,
                                                   message: message,
                                                   ts: Util.now()})
    {:reply, :ok, socket}
  end
end
