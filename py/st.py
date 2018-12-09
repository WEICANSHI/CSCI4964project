from __future__ import print_function
import json
from os.path import join, dirname
from watson_developer_cloud import SpeechToTextV1
from watson_developer_cloud.websocket import RecognizeCallback, AudioSource
import threading


service = SpeechToTextV1(
    ## url is optional, and defaults to the URL below. Use the correct URL for your region.
    url='https://stream.watsonplatform.net/speech-to-text/api',
    iam_apikey='your_apikey')

service = SpeechToTextV1(
    iam_apikey='M8g1a5RlAPntC1DqYYvuEUtiQqGf0Et0CrcsveRJWLSj',
    url='https://stream.watsonplatform.net/speech-to-text/api')

f = open('stotoutput.txt','w')


model = service.get_model('en-US_BroadbandModel').get_result()
#print(json.dumps(model, indent=2))

with open(join(dirname(__file__), './speech.wav'),
          'rb') as audio_file:
    f.write(json.dumps(
        service.recognize(
            audio=audio_file,
            content_type='audio/wav',
            timestamps=True,
            word_confidence=True).get_result(),
        indent=2))
f.close()

f = open('stotoutput.txt','r')
# f1 = open('finaloutput.txt','w')
line="random"
while (line.replace(" ","")[1]!='t'):
    line=f.readline()
# f1.write(line.strip().strip("\"transcript\": \'").strip("\",")) 
print(line.strip().strip("\"transcript\": \'").strip("\","))   
#print("end")
f.close()
# f1.close()