#include<iostream>
#include<unordered_map>
#include<vector>
using namespace std;

int main(){
    vector<int> vec(3,45);
    for(int i=0;i<3;i++)
        cout<<vec[i]<<" ";
    cout<<endl;
    unordered_map<char,vector<int> > mp;
    mp['a'] = vec;
    vector<int> vec1 = mp['a'];

    for(int i=0;i<3;i++)
        vec1[i]+=10;
    for(int i=0;i<3;i++)
        cout<<vec[i]<<" ";
    cout<<endl;
    for(int i=0;i<3;i++)
        cout<<vec1[i]<<" ";
}