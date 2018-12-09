from watson_developer_cloud import TextToSpeechV1
import pyaudio
import wave
import sys

text_to_speech = TextToSpeechV1(
    iam_apikey='YhlhF6VOMkNH9e3Jue9bsFUUkZ4-tendyHtH0zYcxClN',
    url="https://stream.watsonplatform.net/text-to-speech/api"
)

print("start");
with open('hello_world.wav', 'wb') as audio_file:
    audio_file.write(
        text_to_speech.synthesize(
            " ".join(sys.argv[1].split("_")),
            'audio/wav',
            'en-US_AllisonVoice'
        ).get_result().content)

chunk = 1024
wf = wave.open('hello_world.wav', 'rb')
p = pyaudio.PyAudio()

stream = p.open(
    format = p.get_format_from_width(wf.getsampwidth()),
    channels = wf.getnchannels(),
    rate = wf.getframerate(),
    output = True)
data = wf.readframes(chunk)

while data != b'':
    stream.write(data)
    data = wf.readframes(chunk)

stream.close()
p.terminate()